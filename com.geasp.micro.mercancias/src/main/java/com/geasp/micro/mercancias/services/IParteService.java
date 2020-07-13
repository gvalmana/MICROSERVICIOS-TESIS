package com.geasp.micro.mercancias.services;

import java.time.LocalDate;

public interface IParteService<R> {

	public R makeParte(LocalDate date);
}
