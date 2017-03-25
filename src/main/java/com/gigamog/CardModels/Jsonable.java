package com.gigamog.cardmodels;

import com.google.gson.Gson;

/*
 * this class is here so I can serialize to json file.
 */

public abstract class Jsonable {
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
