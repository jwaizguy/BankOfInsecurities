package com.command.checkCommand;

import java.util.LinkedList;

public class MacroCheck implements CheckCommand {
	private LinkedList<CheckCommand> checks;
	private String errorMessage;
	
	public MacroCheck() {
		this.checks = new LinkedList<CheckCommand>();
	}
	
	public MacroCheck(LinkedList<CheckCommand> checks) {
		this.checks = checks;
	}
	
	public void addCheck(CheckCommand newCheck) {
		this.checks.add(newCheck);
	}

	@Override
	public boolean checkIsValid() {
		for(CheckCommand checkCommand : this.checks) {
			if(! checkCommand.checkIsValid()) {
				this.errorMessage = checkCommand.getErrorMessage();
				return false;
			}
		}
		
		return true;
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}
}
