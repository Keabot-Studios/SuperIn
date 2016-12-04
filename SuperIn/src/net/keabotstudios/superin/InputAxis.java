package net.keabotstudios.superin;

import java.lang.reflect.Field;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;

public class InputAxis {

	public static final int EMPTY = -1;

	private final String name;
	private int keyCode = EMPTY, mouseCode = EMPTY;
	private Identifier identifier = null;
	private float actZone = 0.0f;

	public InputAxis(String name, int keyCode, Identifier identifier, float actZone, int mouseCode) {
		this.name = name;
		this.keyCode = keyCode;
		this.identifier = identifier;
		this.actZone = actZone;
		this.mouseCode = mouseCode;
	}

	public InputAxis(String name, int keyCode) {
		this.name = name;
		this.keyCode = keyCode;
	}

	public String getName() {
		return name;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	public float getActZone() {
		return actZone;
	}

	public int getMouseCode() {
		return mouseCode;
	}

	public static Identifier getIdentifierFromName(String s) {
		if(s.equalsIgnoreCase("null"))
			return null;
		String[] split = s.split("\\.");
		try {
			if (split.length == 2) {
				String typeName = split[0];
				String identifier = split[1];
				Class<?>[] declaredClasses = Component.Identifier.class.getDeclaredClasses();
				for (int i = 0; i < declaredClasses.length; i++) {
					if (declaredClasses[i].getName().contains(typeName)) {
						Field[] declaredFields = declaredClasses[i].getDeclaredFields();
						for (int j = 0; j < declaredFields.length; j++) {
							if (declaredFields[j].getName().equals(identifier)) {
								if (declaredFields[j].get(null) instanceof Identifier) {
									return (Identifier) declaredFields[j].get(null);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {}
		System.err.println("Unable to get identifier from name: " + s);
		return null;
	}

}
