package by.htp.library.controller;

import java.util.HashMap;
import java.util.Map;

import by.htp.library.controller.command.Command;
import by.htp.library.controller.command.impl.AddBookAdminCommand;
import by.htp.library.controller.command.impl.AddUserAdminCommand;
import by.htp.library.controller.command.impl.AllBookSearchCommand;
import by.htp.library.controller.command.impl.AllBookSearchPageCommand;
import by.htp.library.controller.command.impl.AllUserSearchCommand;
import by.htp.library.controller.command.impl.AllUserSearchPageCommand;
import by.htp.library.controller.command.impl.DelBookAdminCommand;
import by.htp.library.controller.command.impl.DelUserAdminCommand;
import by.htp.library.controller.command.impl.EditBookAdminCommand;
import by.htp.library.controller.command.impl.KaufBookAdminCommand;
import by.htp.library.controller.command.impl.LesenBookAdminCommand;
import by.htp.library.controller.command.impl.LoginationCommand;
import by.htp.library.controller.command.impl.RegistationCommand;
import by.htp.library.controller.command.impl.SearchBookAdminCommand;
import by.htp.library.controller.command.impl.SearchBookAutorNameCommand;
import by.htp.library.controller.command.impl.SearchFirstNamAdminCommand;
import by.htp.library.controller.command.impl.SearchUserIdAdminCommand;
import by.htp.library.controller.command.impl.UpdUserFirstNamAdminCommand;
import by.htp.library.controller.command.impl.UpdUserpoIdAdminCommand;
import by.htp.library.controller.command.impl.ZakazAllUserCommand;
import by.htp.library.controller.command.impl.ZakazAllUserPageCommand;
import by.htp.library.controller.command.impl.ZakazEinUserCommand;
import by.htp.library.controller.command.impl.ZakazEinUserPageCommand;

public class CommandProvider {
	
	private Map<String, Command> commands = new HashMap<>();
	
	public CommandProvider() {
		commands.put("logination", new LoginationCommand());
		commands.put("registration", new RegistationCommand());
		commands.put("allusersearch", new AllUserSearchCommand());
		commands.put("allusersearchpage", new AllUserSearchPageCommand());
		commands.put("adduseradmin", new AddUserAdminCommand());
		commands.put("deluseradmin", new DelUserAdminCommand());
		commands.put("upduseradminfirstnam", new UpdUserFirstNamAdminCommand());
		commands.put("searchuserfirstnamadmin", new SearchFirstNamAdminCommand());
		commands.put("searchuseridadmin", new SearchUserIdAdminCommand());
		commands.put("upduseradmintable", new UpdUserpoIdAdminCommand());
		
		commands.put("allbooksearch", new AllBookSearchCommand());
		commands.put("allbooksearchpage", new AllBookSearchPageCommand());
		commands.put("addeinbookadmin", new AddBookAdminCommand());
		commands.put("searcheinbookadmin", new SearchBookAdminCommand());
		commands.put("editeinbookadmin", new EditBookAdminCommand());
		commands.put("lesenbookadmin", new LesenBookAdminCommand());
		commands.put("deleinbookadmin", new DelBookAdminCommand());
		commands.put("kaufeinbook", new KaufBookAdminCommand());
		//commands.put("delbookadmin", new DelBookAdminCommand());
		commands.put("searchbookautornameadmin", new SearchBookAutorNameCommand());
		
		commands.put("allkaufzakazalluser", new ZakazAllUserCommand());
		commands.put("allkaufzakazalluserpage", new ZakazAllUserPageCommand());
		commands.put("allkaufzakazeinuser", new ZakazEinUserCommand());
		commands.put("allkaufzakazeinuserpage", new ZakazEinUserPageCommand());
	}

	public Command getCommand(String commandName) {
		return commands.get(commandName);
	}

}
