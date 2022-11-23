package javaserver;

public interface RootServerInterface {
	abstract void toClient(String msg);
	default String[] toGui(String id, String msg) {
		return new String[] {id, msg};
	};
}
