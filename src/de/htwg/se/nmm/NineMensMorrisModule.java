package de.htwg.se.nmm;

import com.google.inject.AbstractModule;

import de.htwg.se.nmm.model.IGamefield;
import de.htwg.se.nmm.model.IPlayer;

public class NineMensMorrisModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(IGamefield.class).to(de.htwg.se.nmm.model.impl.Gamefield.class);

	}

}