import com.temas.netlobby.core.bootstrap.ApplicationBuilder
import com.temas.netlobby.core.bootstrap.ServerBuilder
import com.temas.netlobby.core.net.udp.UDPServer
import com.temas.netlobby.core.status.*
import com.temas.netlobby.server.updatesender.TimerService
import com.temas.netlobby.server.updatesender.UpdateSender
import io.netty.channel.ChannelFuture
import org.junit.Rule
import org.junit.Test
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.spy
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

/**
 * Created by azhdanov on 19.05.2023.
 */
class FlowTest {
    open class TestModel() {
        fun update() {}
    }

    open class TestUpdateBuilder() {
        fun build() = DummyModel()
    }

     class UpdatesHandler(): InboundHandler {
         override fun invoke(state: ServerState) {}

     }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        // Your way to build a Mock here
        Mockito.mock(clazz.java)
    }

    @Test
    fun `send action, update model, send updates, receive updates`() {

        val model = spy(TestModel())
        val spyUpdateBuilder = spy(TestUpdateBuilder())
        val spyUpdatesHandler = spy(UpdatesHandler())
        val app = ApplicationBuilder()
            .withActionHandler {
                model.update()
                it.id
            }
            .withUpdateBuilder { spyUpdateBuilder.build() }
            .buildKoin()
        val udpServerMock = app.koin.declareMock<UDPServer>()
        Mockito.`when`(udpServerMock.init()).thenReturn(Mockito.mock(ChannelFuture::class.java))
        val updateSender = spy(app.koin.get<UpdateSender>())
        val timerMock = app.koin.declareMock<TimerService>()
        Mockito.`when`(timerMock.start(any())).then { updateSender.sendUpdates() }

        val server = ServerBuilder()
            .withApplication(app)
            .updatePeriod(100)
            .build()

        val client = server.createLocalClient(spyUpdatesHandler::invoke)
        server.start()

        val connection = client.connect()
        connection.sendActions(listOf(Action(DummyActionType())))
        verify(udpServerMock, times(1)).init()
        verify(model, times(1)).update()
        verify(spyUpdateBuilder, times(1)).build()
        verify(updateSender, times(1)).sendUpdates()
        verify(spyUpdatesHandler, times(1)).invoke(any())
    }
}