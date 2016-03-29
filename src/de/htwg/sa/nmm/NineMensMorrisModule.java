package de.htwg.sa.nmm;

import com.google.inject.AbstractModule;

import de.htwg.sa.nmm.model.IGamefield;
import de.htwg.sa.nmm.model.IPlayer;

public class NineMensMorrisModule extends AbstractModule {

	@Override
	protected void configure() {
		//bind(IGamefield.class).to(de.htwg.sa.nmm.model.impl.Gamefield.class);
	}

}