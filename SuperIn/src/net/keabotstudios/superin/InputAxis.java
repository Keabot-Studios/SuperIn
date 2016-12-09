package net.keabotstudios.superin;

import java.lang.reflect.Field;
import java.util.Arrays;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;

public class InputAxis {

	public static final int EMPTY = -1;

	private final String name;
	private int keyCode = EMPTY, mouseCode = EMPTY;
	private Identifier[] identifiers = null;
	private float actZone = 0.0f;

	public InputAxis(String name, int keyCode, Identifier identifier, float actZone, int mouseCode) {
		this.name = name;
		this.keyCode = keyCode;
		this.identifiers = new Identifier[1];
		if(identifier == null) throw new NullPointerException("Identifier array should not be null, empty, or contain a null identfier!");
		this.identifiers[0] = identifier;
		this.actZone = actZone;
		this.mouseCode = mouseCode;
	}
	
	public InputAxis(String name, int keyCode, Identifier[] identifiers, float actZone, int mouseCode) {
		this.name = name;
		this.keyCode = keyCode;
		if(identifiers.equals(null) || identifiers.length == 0 || Arrays.asList(identifiers).contains(null)) throw new NullPointerException("Identifier array should not be null, empty, or contain a null identfier!");
		this.identifiers = identifiers;
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

	public Identifier[] getIdentifiers() {
		return identifiers;
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
