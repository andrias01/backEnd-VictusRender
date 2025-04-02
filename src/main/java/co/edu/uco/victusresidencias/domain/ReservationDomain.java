package co.edu.uco.victusresidencias.domain;

import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class ReservationDomain extends Domain {

    private int guestNumber;
    private TurnDomain turn;
    private ResidentDomain resident;

    // Constructor privado
    private ReservationDomain(final UUID id, final int guestNumber, final TurnDomain turn, final ResidentDomain resident) {
        super(id);
        setGuestNumber(guestNumber);
        setTurn(turn);
        setResident(resident);
    }

    // Método estático para crear una instancia con parámetros
    public static ReservationDomain create(final UUID id, final int guestNumber, final TurnDomain turn, final ResidentDomain resident) {
        return new ReservationDomain(id, guestNumber, turn, resident);
    }

    // Método estático para crear una instancia por defecto
    public static ReservationDomain create() {
        return new ReservationDomain(UUIDHelper.getDefault(), NumericHelper.CERO, TurnDomain.create(), ResidentDomain.create());
    }

    // Getters y Setters

    public int getGuestNumber() {
        return guestNumber;
    }

    private void setGuestNumber(final int guestNumber) {
        this.guestNumber = NumericHelper.getDefault(guestNumber, NumericHelper.CERO);
    }

    public TurnDomain getTurn() {
        return turn;
    }

    private void setTurn(final TurnDomain turn) {
        this.turn = (turn != null) ? turn : TurnDomain.create();
    }

    public ResidentDomain getResident() {
        return resident;
    }

    private void setResident(final ResidentDomain resident) {
        this.resident = (resident != null) ? resident : ResidentDomain.create();
    }

    @Override
    public UUID getId() {
        return super.getId();
    }
}
