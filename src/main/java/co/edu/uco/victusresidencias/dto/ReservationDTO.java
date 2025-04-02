package co.edu.uco.victusresidencias.dto;

import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class ReservationDTO extends DomainDTO {

    private int guestNumber;
    private TurnDTO turn;
    private ResidentDTO resident;

    private ReservationDTO() {
        super(UUIDHelper.getDefaultAsString());
        setGuestNumber(NumericHelper.CERO);
        setTurn(TurnDTO.create());
        setResident(ResidentDTO.create());
    }

    public static ReservationDTO create() {
        return new ReservationDTO();
    }

    public int getGuestNumber() {
        return guestNumber;
    }

    public ReservationDTO setGuestNumber(int guestNumber) {
        this.guestNumber = NumericHelper.getDefault(guestNumber, NumericHelper.CERO);
        return this;
    }

    public TurnDTO getTurn() {
        return turn;
    }

    public ReservationDTO setTurn(TurnDTO turn) {
        this.turn = ObjectHelper.getDefault(turn, TurnDTO.create());
        return this;
    }

    public ResidentDTO getResident() {
        return resident;
    }

    public ReservationDTO setResident(ResidentDTO resident) {
        this.resident = ObjectHelper.getDefault(resident, ResidentDTO.create());
        return this;
    }

    public ReservationDTO setId(final String id) {
        super.setIdentifier(id);
        return this;
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
