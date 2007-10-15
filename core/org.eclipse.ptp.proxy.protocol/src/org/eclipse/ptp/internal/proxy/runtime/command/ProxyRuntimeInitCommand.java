package org.eclipse.ptp.internal.proxy.runtime.command;

import org.eclipse.ptp.proxy.client.IProxyClient;
import org.eclipse.ptp.proxy.command.AbstractProxyCommand;
import org.eclipse.ptp.proxy.runtime.command.IProxyRuntimeCommand;

public class ProxyRuntimeInitCommand extends AbstractProxyCommand implements IProxyRuntimeCommand {
	
	public ProxyRuntimeInitCommand(int baseId) {
		addArgument(IProxyClient.WIRE_PROTOCOL_VERSION);
		addArgument(baseId);
	}

	public int getCommandID() {
		return INIT;
	}
}
