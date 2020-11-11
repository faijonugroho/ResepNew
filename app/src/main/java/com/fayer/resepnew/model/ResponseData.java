package com.fayer.resepnew.model;

import com.google.gson.annotations.SerializedName;

public class ResponseData {

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("kode")
	private int kode;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}