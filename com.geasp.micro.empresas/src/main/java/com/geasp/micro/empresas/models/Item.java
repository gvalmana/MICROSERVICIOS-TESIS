package com.geasp.micro.empresas.models;

import org.springframework.data.annotation.Id;

public class Item {

	@Id
	public String _id;
	    
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
	
}
