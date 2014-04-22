package estore.entity;

public class Rent {
	private int id;
	private int user_id;
	private int copy_id;
	private String rent_date;
	private String due_date;
	private String return_date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getCopy_id() {
		return copy_id;
	}

	public void setCopy_id(int copy_id) {
		this.copy_id = copy_id;
	}

	public String getRent_date() {
		return rent_date;
	}

	public void setRent_date(String rent_date) {
		this.rent_date = rent_date;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public String getReturn_date() {
		return return_date;
	}

	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}
}
