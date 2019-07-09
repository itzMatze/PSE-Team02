package kit.edu.mensameet.server.model;

public enum Ingredient {
	WITH_DYE("(1) mit Farbstoff"),
	WITH_PRESERVATIVE("(2) mit Konservierungsstoff"),
	WITH_ANTIOXIDANT("(3) mit Antioxidationsmittel"),
	WITH_FLAVOR_ENHANCER("(4) mit Geschmacksverstärker"),
	WITH_PHOSPHATE("(5) mit Phosphat"),
	WAXED_SURFACE("Oberfläche gewachst"),
	SULPHURIZED("(7) geschwefelt"),
	BLACKENED_OLIVES("(8) Oliven geschwärzt"),
	WITH_SWEETENER("(9) mit Süßungsmittel"),
	MAYBE_LAXATIVE("(10) kann bei übermäßigem Verzehr abführend wirken"),
	CONTAINS_PHENYLALANINE("(11) enthält eine Phenylalaninquelle"),
	CONTAINS_REST_ALCOHOL("(12) kann Restalkohol enthalten"),
	FROM_MEAT_PIECES("(14) aus Fleischstücken zusammengefügt"),
	WITH_FAT_GLAZE("(15) mit kakaohaltiger Fettglasur"),
	FROM_FISH_PIECES("(27) aus Fischstücken zusammengefügt"),
	BEEF("(R) enthält Rindfleisch"),
	REGIONAL_BEEF("(RAT) enthält regionales Rindfleisch aus artgerechter Tierhaltung"),
	PORK("(S) enthält Schweinefleisch"),
	REGIONAL_PORK("(SAT) entha ̈lt regionales Schweinefleisch aus artgerechter Tierhaltung"),
	VEGETARIAN("(VEG) vegetarisches Gericht"),
	VEGAN("(VG) veganes Gericht (ohne Fleischzusatz)"),
	BIO("(B) kontrolliert biologischer Anbau / DE007 O ̈ko Kontrollstelle"),
	MSC_FISH("(MSC) MSC-zertifizierter Fisch"),
	MENSA_VITAL("(MV) MensaVital"),
	LAB("(LAB) mit tierischem Lab"),
	GELATIN("(GER) mit Gelatine"),
	GLUTEN("(Gl) Glutenhaltiges Getreide"),
	WHEAT("(We) Weizen"),
	RYE("(Ro) Roggen"),
	BARLEY("(Ge) Gerste"),
	OAT("(Ha) Hafer"),
	SPELT("(Di) Dinkel"),
	KAMUT("(Ka) Kamut"),
	NUTS("(Nu) Schalenfru ̈chte / Nüsse"),
	ALMOND("(Ma) Mandeln"),
	HAZELNUT("(Ha) Haselnüsse)"),
	WALNUT("(Wa) Walnüsse"),
	CASHEW("(Ca) Cashewnüsse"),
	PECAN("(Pe) Pekanüsse"),
	BRAZILNUT("(Pa) Paranüsse"),
	PISTACHIO("(Pi) Pistazie"),
	MACADAMIA("(Qu) Queenslandüsse/Macadamianüsse"),
	EGGS("(Ei) Eier"),
	PEANUT("(Er) Erdnüsse"),
	SOY("(So) Soja"),
	MUSTARD("(Sn) Senf"),
	CRUSTACEANS("(Kr) Krebstiere"),
	FISH("(Fi) Fisch"),
	LACTOSE("(ML) Milch / Laktose"),
	CELERY("(Se) Sellerie"),
	SULFITE("(Sf) Schwefeldioxid / Sulfit"),
	SESAME("(Sa) Sesam"),
	LUPINE("(Lu) Lupine"),
	MOLLUSCS("(We) Weichtiere");
	
	
	
	
private String name;
	
	private Ingredient(String name) {
		this.name = name;
	}
	
	public String toString(){
        return name;
    }
	

}
