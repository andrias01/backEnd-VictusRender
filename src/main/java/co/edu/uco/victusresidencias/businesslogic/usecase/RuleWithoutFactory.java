package co.edu.uco.victusresidencias.businesslogic.usecase;

public interface RuleWithoutFactory<T> {
	void execute(T data, String nameAtributte);
}
