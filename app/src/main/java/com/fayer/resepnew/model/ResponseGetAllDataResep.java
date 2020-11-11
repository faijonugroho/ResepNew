package com.fayer.resepnew.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseGetAllDataResep{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("sukses")
	private String sukses;

	@SerializedName("resep")
	private List<ResepItem> resep;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setSukses(String sukses){
		this.sukses = sukses;
	}

	public String getSukses(){
		return sukses;
	}

	public void setResep(List<ResepItem> resep){
		this.resep = resep;
	}

	public List<ResepItem> getResep(){
		return resep;
	}
}