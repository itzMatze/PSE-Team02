package edu.kit.mensameet.client.model;

import android.content.Context;

import androidx.annotation.StringRes;

import edu.kit.mensameet.client.view.R;

/**
 * Enumeration for university subjects.
 */
public enum Subject implements IdEnum {

    APPLIED_GEOSCIENCES(R.string.subject_applied_geosciences),
    ARCHITECTURE(R.string.subject_architecture),
    BIOENGINEERING(R.string.subject_bioengineering),
    BIOLOGY(R.string.subject_biolgy),
    BUILDING_RESTORATION(R.string.subject_building_restoration),
    CHEMICAL_AND_PROCESS_ENGINEERING(R.string.subject_chemical_and_process_engineering),
    CHEMICAL_BIOLOGY(R.string.subject_chemical_biology),
    CHEMISTRY(R.string.subject_chemistry),
    CIVIL_ENGINEERING(R.string.subject_civil_engineering),
    ECONOMATHEMATICS(R.string.subject_economathematics),
    ECONOMICS_ENGINEERING(R.string.subject_economics_engineering),
    ELECTRICAL_ENGINEERING_AND_INFORMATION_TECHNOLOGY(R.string.subject_electrical_engineering_and_information_technology),
    ENERGY_ENGINEERING_AND_MANAGEMENT(R.string.subject_energy_engineering_and_management),
    ENGINEERING_PEDAGOGICS(R.string.subject_engineering_pedagogics),
    EUROPEAN_CULTURE_AND_HISTORY_OF_IDEAS(R.string.subject_european_culture_and_history_of_ideas),
    FINANCIAL_ENGINEERING(R.string.subject_financial_engineering),
    FOOD_CHEMISTRY(R.string.subject_food_chemistry),
    GEODESY_AND_GEOINFORMATICS(R.string.subject_geodesy_and_geoinformatics),
    GEOECOLOGY(R.string.subject_geoecology),
    GEOGRAPHY(R.string.subject_geography),
    GEOPHYSICS(R.string.subject_geophysics),
    GERMAN_LITERATURE(R.string.subject_german_literature),
    HISTORY_OF_ART(R.string.subject_history_of_art),
    INFORMATICS(R.string.subject_it),
    INFORMATION_SYSTEMS_ENGINEERING_AND_MANAGEMENT(R.string.subject_information_systems_engineering_and_management),
    MANAGEMENT_OF_PRODUCT_DEVELOPMENT(R.string.subject_management_of_product_development),
    MATERIALS_SCIENCE_AND_ENGINEERING(R.string.materials_science_and_engineering),
    MATHEMATICS(R.string.subject_math),
    MECHANICAL_ENGINEERING(R.string.mechanical_engineering),
    MECHATRONICS_AND_INFORMATION_TECHNOLOGY(R.string.subject_mechatronics_and_information_technology),
    METEOROLOGY(R.string.subject_meteorology),
    MOBILITY_AND_INFRASTRUCTURE(R.string.subject_mobility_and_infrastructure),
    MOBILITY_SYSTEMS_ENGINEERING_AND_MANAGEMENT(R.string.subject_mobility_systems_engineering_and_management),
    NATURALSCIENCES_AND_TECHNOLOGIE(R.string.subject_naturalsciences_and_technologie),
    OPTICS_AND_PHOTONICS(R.string.subject_optics_and_photonics),
    PEDAGOGICS(R.string.subject_pedagogics),
    PHILOSOPHY_ETHICS(R.string.subject_philosophy_ethics),
    PHYSICS(R.string.subject_physics),
    PRODUCTION_AND_OPERATIONS_MANAGEMENT(R.string.subject_production_and_operations_management),
    REGIONAL_SCIENCES(R.string.subject_regional_sciences),
    REMOTE_SENSING_AND_GEOINFORMATICS(R.string.subject_remote_sensing_and_geoinformatics),
    SCIENCE__MEDIA__COMMUNICATION(R.string.subject_science__media__communication),
    SPORTS(R.string.subject_sports),
    TECHNO_MATHEMATICS(R.string.subject_techno_mathematics),
    WATER_SCIENCE_AND_ENGINEERING(R.string.subject_water_science_and_engineering);

    private final @StringRes
    int id;

    private Subject(@StringRes int id) {
        this.id = id;
    }

    public static Subject valueOfId(@StringRes int id) {
        for (Subject e : values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

    public static Subject valueOfString(Context context, String string) {
        for (Subject e : values()) {
            if (context.getResources().getString(e.id) == string) {
                return e;
            }
        }
        return null;
    }

    @Override
    public int getId() {
        return id;
    }

}