package com.geasp.micro.partes.services;

import com.geasp.micro.partes.models.Parte;

public interface IParte {

	Parte getParteByDate(String date);

	Parte parteFallCallBack(String date);
}
