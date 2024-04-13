package sample;


import java.util.HashSet;
import java.util.Set;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.springframework.lang.Nullable;

import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.Metamodel;

@State(Scope.Thread)
public class StringBenchmark {

    private Metamodel[] metamodels;
    private Class<?> testClass;

    @Setup
    public void setup() {
        // Initialize metamodels and testClass
        Set<Metamodel> metamodelSet = new HashSet<>();
        // Add Metamodel instances to metamodelSet
        metamodels = metamodelSet.toArray(new Metamodel[0]);
        testClass = String.class; // Example initialization
    }

    @Benchmark
    public Metamodel testGetMetamodelForFirst() {
        return getMetamodelForFirst(testClass);
    }

    @Benchmark
    public Metamodel testGetMetamodelForSecond() {
        return getMetamodelForSecond(testClass);
    }

    @Nullable
	private Metamodel getMetamodelForFirst(Class<?> type) {

		for (Metamodel model : metamodels) {

			try {
				model.managedType(type);
				return model;
			} catch (IllegalArgumentException o_O) {

				// Fall back to inspect *all* managed types manually as Metamodel.managedType(…) only
				// returns for entities, embeddables and managed superclasses.

				for (ManagedType<?> managedType : model.getManagedTypes()) {
					if (type.equals(managedType.getJavaType())) {
						return model;
					}
				}
			}
		}

		return null;
	}

    @Nullable
	private Metamodel getMetamodelForSecond(Class<?> type) {
		Metamodel currentModel = null;
		try {
			for (Metamodel model : metamodels) {
				currentModel = model;
				model.managedType(type);
				return model;
			}
		} catch (IllegalArgumentException o_O) {

			// Fall back to inspect *all* managed types manually as Metamodel.managedType(…) only
			// returns for entities, embeddables and managed superclasses.

			for (ManagedType<?> managedType : currentModel.getManagedTypes()) {
				if (type.equals(managedType.getJavaType())) {
					return currentModel;
				}
			}
		}
		return null;
	}

}