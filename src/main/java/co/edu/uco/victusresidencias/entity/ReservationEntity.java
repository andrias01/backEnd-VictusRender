package co.edu.uco.victusresidencias.entity;

import java.util.UUID;


import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class ReservationEntity extends DomainEntity {
	
	private int guestsNumber;
	private TurnEntity turn;
	private ResidentEntity resident;


	public ReservationEntity() {
		super(UUIDHelper.getDefault());
		setGuestsNumber(NumericHelper.CERO);
		setTurn(new TurnEntity());
		setResident(new ResidentEntity());
	}
	
	public int getGuestsNumber() {
		return guestsNumber;
	}

	public void setGuestsNumber(int guestsNumber) {
		this.guestsNumber = guestsNumber;
	}

	@Override
	public void setId(final UUID id) {
		super.setId(id);
	}
	@Override
	public UUID getId() {
		return super.getId();
	}

	public TurnEntity getTurn() {
		return turn;
	}

	public void setTurn(final TurnEntity turn) {
		this.turn = ObjectHelper.getDefault(turn, new TurnEntity());
	}
	
	public ResidentEntity getResident() {
		return resident;
	}

	public void setResident(final ResidentEntity resident) {
		this.resident = ObjectHelper.getDefault(resident, new ResidentEntity());
	}
	

}