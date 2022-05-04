package com.girmiti.nexo.server.corelauncher;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.girmiti.nexo.netty.RequestListner;
@RunWith(MockitoJUnitRunner.Silent.class)
public class ClientHandlerTest {
	
	@Test
	public void testClientHandler() {
		RequestListner nexofastListner = new RequestListner();
		nexofastListner.connectPort(20, 100, 000, 100);
		ClientHandler clientHandler=new ClientHandler(300);
		Assert.assertNotNull(nexofastListner);
	}

}
