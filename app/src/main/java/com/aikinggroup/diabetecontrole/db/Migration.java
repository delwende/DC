

package com.aikinggroup.diabetecontrole.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Set;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmFieldType;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/************************************************
 Current Database Schema

 class User
 @PrimaryKey int id;
 String name;
 String preferred_language;
 String country;
 int age;
 String gender;
 int d_type;
 String preferred_unit;
 String preferred_unit_a1c;
 String preferred_unit_weight;
 String preferred_range;
 double custom_range_min;
 double custom_range_max;

 class Reminder
 @PrimaryKey long id;
 Date alarmTime;
 boolean oneTime;
 String metric;

 class CholesterolReading
 @PrimaryKey long id;
 double totalReading;
 double LDLReading;
 double HDLReading;
 Date created;

 class GlucoseReading
 @PrimaryKey long id;
 double reading;
 String reading_type;
 String notes;
 int user_id;
 Date created;

 class KetoneReading
 @PrimaryKey long id;
 double reading;
 Date created;

 class PressureReading
 @PrimaryKey long id;
 double minReading;
 double maxReading;
 Date created;

 class WeightReading
 @PrimaryKey long id;
 double reading;
 Date created;

 class HB1ACReading
 @PrimaryKey long id;
 double reading;
 Date created;
 ************************************************/

class Migration implements RealmMigration {

    private Class[] _models;

    public Migration(Class[] models) {
        _models = models;
    }



    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
            createInitialSchema(schema);
            oldVersion++;
        }

        if (oldVersion == 1) {
            // Change HB1AC reading from int to double
            changeHB1ACReadingsToDouble(schema);

            oldVersion++;
        }

        if (oldVersion == 2) {
            // Add 2 new fields in User:
            // String preferred_unit_a1c
            // String preferred_unit_weight
            // and populate them with default values (% and kilograms)
            addWeightAndA1CUnitsToUser(schema);

            oldVersion++;
        }

        if (oldVersion == 3) {
            // Add Reminders
            addReminders(schema);

            oldVersion++;
        }

        if (oldVersion == 4) {
            migrateAllReadingsToDouble(schema);

            oldVersion++;
        }
        for (Class classModel : _models) {
            final RealmObjectSchema userSchema = schema.get(classModel.getSimpleName());

            Field[] fields = classModel.getDeclaredFields();
            Set<String> fieldsDatabase = userSchema.getFieldNames();

            for (String fieldDatabase : fieldsDatabase) {

                Boolean removed = true;

                for (Field field : fields) {
                    if (field.getName().compareToIgnoreCase(fieldDatabase) == 0) {
                        removed = false;
                        break;
                    }
                }

                if (removed) {
                    userSchema.removeField(fieldDatabase);
                }
            }

            for (Field field : fields) {
                Class type = field.getType();
                String name = field.getName();
                if (!userSchema.hasField(name)){
                    userSchema.addField(name, GetClassOfType(type));
                }

            }
        }
    }

    private Class GetClassOfType(Class type) {
        String name = type.getSimpleName();

        if (name.compareToIgnoreCase("string") == 0) {
            return String.class;
        } else if (name.compareToIgnoreCase("int") == 0) {
            return int.class;
        } else if (name.compareToIgnoreCase("boolean") == 0) {
            return Boolean.class;
        }  else if (name.compareToIgnoreCase("byte") == 0) {
            return byte.class;
        }  else if (name.compareToIgnoreCase("byte[]") == 0) {
            return byte[].class;
        }

        return Boolean.class;
    }
    /*@Override
    public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            createInitialSchema(schema);
            oldVersion++;
        }

        if (oldVersion == 1) {
            // Change HB1AC reading from int to double
            changeHB1ACReadingsToDouble(schema);

            oldVersion++;
        }

        if (oldVersion == 2) {
            // Add 2 new fields in User:
            // String preferred_unit_a1c
            // String preferred_unit_weight
            // and populate them with default values (% and kilograms)
            addWeightAndA1CUnitsToUser(schema);

            oldVersion++;
        }

        if (oldVersion == 3) {
            // Add Reminders
            addReminders(schema);

            oldVersion++;
        }

        if (oldVersion == 4) {
            migrateAllReadingsToDouble(schema);

            oldVersion++;
        }
    }
*/
    private void migrateAllReadingsToDouble(RealmSchema schema) {
        // Change Weight reading from int to double
        RealmObjectSchema objectSchema = schema.get(RObject.WEIGHT.key());
        safeMigrationIntToDouble(objectSchema, RealmField.READING.key());

        // Change Pressure max and min readings from int to double
        objectSchema = schema.get(RObject.PRESSURE.key());
        safeMigrationIntToDouble(objectSchema, RealmField.MIN_READING.key());
        safeMigrationIntToDouble(objectSchema, RealmField.MAX_READING.key());

        // Change Glucose reading from int to double
        objectSchema = schema.get(RObject.GLUCOSE.key());
        safeMigrationIntToDouble(objectSchema, RealmField.READING.key());

        // Change Cholesterol total, ldl and hdl readings from int to double
        safeMigrationIntToDouble(objectSchema, RealmField.TOTAL_READING.key());
        safeMigrationIntToDouble(objectSchema, RealmField.LDL_READING.key());
        safeMigrationIntToDouble(objectSchema, RealmField.HDL_READING.key());

        // Change User custom range min and max from int to double
        objectSchema = schema.get(RObject.USER.key());
        safeMigrationIntToDouble(objectSchema, RealmField.CUSTOM_RANGE_MIN.key());
        safeMigrationIntToDouble(objectSchema, RealmField.CUSTOM_RANGE_MAX.key());
    }

    private void addReminders(RealmSchema schema) {
        schema.create(RObject.REMINDER.key())
                .addField(RealmField.ID.key(), Long.class, FieldAttribute.PRIMARY_KEY, FieldAttribute.REQUIRED)
                .addField(RealmField.METRIC.key(), String.class)
                .addField(RealmField.ALARM_TIME.key(), Date.class)
                .addField(RealmField.ACTIVE.key(), Boolean.class, FieldAttribute.REQUIRED)
                .addField(RealmField.ONE_TIME.key(), Boolean.class, FieldAttribute.REQUIRED)
                .addField(RealmField.LABEL.key(), String.class);
    }

    private void addWeightAndA1CUnitsToUser(RealmSchema schema) {
        schema.get(RObject.USER.key())
                .addField(RealmField.PREFERRED_UNIT_A1C.key(), String.class, FieldAttribute.REQUIRED)
                .addField(RealmField.PREFERRED_UNIT_WEIGHT.key(), String.class, FieldAttribute.REQUIRED)
                .transform(new RealmObjectSchema.Function() {
                    @Override
                    public void apply(DynamicRealmObject obj) {
                        obj.set(RealmField.PREFERRED_UNIT_A1C.key(), "percentage");
                        obj.set(RealmField.PREFERRED_UNIT_WEIGHT.key(), "kilograms");

                    }
                });
    }

    private void changeHB1ACReadingsToDouble(RealmSchema schema) {
        RealmObjectSchema objectSchema = schema.get(RObject.HB_1_AC.key());
        safeMigrationIntToDouble(objectSchema, RealmField.READING.key());
    }

    private void createInitialSchema(RealmSchema schema) {
        schema.create(RObject.WEIGHT.key())
                .addField(RealmField.ID.key(), Long.class, FieldAttribute.PRIMARY_KEY, FieldAttribute.REQUIRED)
                .addField(RealmField.CREATED.key(), Date.class)
                .addField(RealmField.READING.key(), Integer.class, FieldAttribute.REQUIRED);

        schema.create(RObject.PRESSURE.key())
                .addField(RealmField.ID.key(), Long.class, FieldAttribute.PRIMARY_KEY, FieldAttribute.REQUIRED)
                .addField(RealmField.CREATED.key(), Date.class)
                .addField(RealmField.MIN_READING.key(), Integer.class, FieldAttribute.REQUIRED)
                .addField(RealmField.MAX_READING.key(), Integer.class, FieldAttribute.REQUIRED);

        schema.create(RObject.HB_1_AC.key())
                .addField(RealmField.ID.key(), Long.class, FieldAttribute.PRIMARY_KEY, FieldAttribute.REQUIRED)
                .addField(RealmField.CREATED.key(), Date.class)
                .addField(RealmField.READING.key(), Integer.class, FieldAttribute.REQUIRED);
    }

    private void safeMigrationIntToDouble(@Nullable RealmObjectSchema objectSchema, @NonNull String columnName) {
        if (objectSchema != null) {
            migrateIntColumnToDouble(objectSchema, columnName);
        }
    }

    private void migrateIntColumnToDouble(@NonNull RealmObjectSchema objectSchema, @NonNull final String columnName) {
        final String tempColumnName = columnName + "_tmp";
        objectSchema
                .addField(tempColumnName, Double.class, FieldAttribute.REQUIRED)
                .transform(new RealmObjectSchema.Function() {
                    @Override
                    public void apply(@NonNull DynamicRealmObject obj) {
                        int oldType = obj.getInt(columnName);
                        obj.setDouble(tempColumnName, oldType);
                    }
                })
                .removeField(columnName)
                .renameField(tempColumnName, columnName);
    }
    private void migrateExistingFields(@NonNull RealmObjectSchema objectSchema, @NonNull final String columnName,Class type) {
        final String tempColumnName = columnName + "_tmp";
        objectSchema
                .addField(tempColumnName, type, FieldAttribute.REQUIRED)
                .transform(new RealmObjectSchema.Function() {
                    @Override
                    public void apply(@NonNull DynamicRealmObject obj) {
                        RealmFieldType oldType = obj.getFieldType(columnName);
                        obj.set(tempColumnName, oldType);
                    }
                })
                .removeField(columnName)
                .renameField(tempColumnName, columnName);
    }
}
