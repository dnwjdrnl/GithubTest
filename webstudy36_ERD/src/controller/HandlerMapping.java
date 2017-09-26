package controller;

public class HandlerMapping {
	private static HandlerMapping instance=new HandlerMapping();
	private HandlerMapping(){}
	public static HandlerMapping getInstance(){
		return instance;
	}
	public Controller create(String command){
		Controller controller=null;
		System.out.println(command);
		if(command.equals("selectHobby"))
		 controller=new SelectHobbyController();
		else if(command.equals("register"))
			controller= new RegisterController();
		else if(command.equals("findById"))
			controller= new FindByIdController();
		return controller;
	}

}
