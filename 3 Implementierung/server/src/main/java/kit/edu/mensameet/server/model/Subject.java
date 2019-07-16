package kit.edu.mensameet.server.model;
/*
 * This enum represents the different subjects a college student or professor can choose from
 */
public enum Subject {
	APPLIED_GEOSCIENCES("Angewandte Geowissenschaften"),
	ARCHITECTURE("Architektur"),
	BIOENGINEERING("Bioingenieurwesen"),
	BIOLOGY("Biologie"),
	BUILDING_RESTORATION("Altbauinstandsetzung"),
	CHEMICAL_AND_PROCESS_ENGINEERING("Chemieingenieurwesen und Verfahrenstechnik"),
	CHEMICAL_BIOLOGY("Chemische Biologie"),
	CHEMISTRY("Chemie"),
	CIVIL_ENGINEERING("Bauingenieurwesen"),
	ECONOMATHEMATICS("Wirtschaftsmathematik"),
	ECONOMICS_ENGINEERING("Wirtschaftsingenieurwesen"),
	ELECTRICAL_ENGINEERING_AND_INFORMATION_TECHNOLOGY("Elektro- und Informationstechnik"),
	ENERGY_ENGINEERING_AND_MANAGEMENT("Energy Engineering and Management"),
	ENGINEERING_PEDAGOGICS("Ingenieurpädagogik"),
	EUROPEAN_CULTURE_AND_HISTORY_OF_IDEAS("Europäische Kultur und Ideengeschichte"),
	FINANCIAL_ENGINEERING("Financial Engineering"),
	FOOD_CHEMISTRY("Lebensmittelchemie"),
	GEODESY_AND_GEOINFORMATICS("Geodäsie und Geoinformatik"),
	GEOECOLOGY("Geoökologie"),
	GEOGRAPHY("Geographie"),
	GEOPHYSICS("Geophysik"),
	GERMAN_LITERATURE("Germanistik"),
	HISTORY_OF_ART("Kunstgeschichte"),
	INFORMATICS("Informatik"),
	INFORMATION_SYSTEMS_ENGINEERING_AND_MANAGEMENT("Information Systems Engineering and Management "),
	MANAGEMENT_OF_PRODUCT_DEVELOPMENT("Management of Product Development "),
	MATERIALS_SCIENCE_AND_ENGINEERING("Materialwissenschaft und Werkstofftechnik"),
	MATHEMATICS("Mathematik"),
	MECHANICAL_ENGINEERING("Mechanical Engineering "),
	MECHATRONICS_AND_INFORMATION_TECHNOLOGY("Mechatronik und Informationstechnik "),
	METEOROLOGY("Meteorologie"),
	MOBILITY_AND_INFRASTRUCTURE("Mobilität und Infrastruktur"),
	MOBILITY_SYSTEMS_ENGINEERING_AND_MANAGEMENT("Mobility Systems Engineering and Management"),
	NATURALSCIENCES_AND_TECHNOLOGIE("Naturwissenschaft und Technik "),
	OPTICS_AND_PHOTONICS("Optics and Photonics"),
	PEDAGOGICS("Pädagogik"),
	PHILOSOPHY_ETHICS("Philosophie/Ethik"),
	PHYSICS("Physik"),
	PRODUCTION_AND_OPERATIONS_MANAGEMENT("Production and Operations Management"),
	REGIONAL_SCIENCES("Regionalwissenschaft"),
	REMOTE_SENSING_AND_GEOINFORMATICS("Remote Sensing and Geoinformatics"),
	SCIENCE__MEDIA__COMMUNICATION("Wissenschaft-Medien-Kommunikation"),
	SPORTS("Sport"),
	TECHNO_MATHEMATICS("Technomathematik"),
	WATER_SCIENCE_AND_ENGINEERING("Water Science and Engineering");
	
	private String name;
	/*
	 * This constructor adds a String containing the name of the subject, in particular the german translation
	 */
	private Subject(String name) {
		this.name = name;
	}
	/*
	 * This mehod returns the name of the subject
	 */
	public String toString(){
        return name;
    }
}