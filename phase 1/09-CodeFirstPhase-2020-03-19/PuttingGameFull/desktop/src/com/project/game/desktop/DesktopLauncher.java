package com.project.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.project.game.Main;
import com.mygdx.puttingame.*;
import com.project.puttingsimulator.*;
import com.project.puttingsimulator.Tools;

import javax.tools.Tool;

public class DesktopLauncher {

	public static void main (String[] arg) {

		SimulateMain.beginning();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new What(), config);

		Tools.wait(1000);

		while (!SimulateMain.win) {
			SimulateMain.start();

			Tools.wait(3000);

			new LwjglApplication(new What(), config);
		}

		}









}

