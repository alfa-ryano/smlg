/**
 */
package eoml.impl;

import eoml.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EomlFactoryImpl extends EFactoryImpl implements EomlFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EomlFactory init() {
		try {
			EomlFactory theEomlFactory = (EomlFactory)EPackage.Registry.INSTANCE.getEFactory(EomlPackage.eNS_URI);
			if (theEomlFactory != null) {
				return theEomlFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EomlFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EomlFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case EomlPackage.EOML: return createEOML();
			case EomlPackage.TRANSITION: return createTransition();
			case EomlPackage.EDUCATIONAL_OBJECTIVE: return createEducationalObjective();
			case EomlPackage.LEARNING_ACTIVITY: return createLearningActivity();
			case EomlPackage.ACTIVITY_OBJECTIVE: return createActivityObjective();
			case EomlPackage.START_NODE: return createStartNode();
			case EomlPackage.END_NODE: return createEndNode();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOML createEOML() {
		EOMLImpl eoml = new EOMLImpl();
		return eoml;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition createTransition() {
		TransitionImpl transition = new TransitionImpl();
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EducationalObjective createEducationalObjective() {
		EducationalObjectiveImpl educationalObjective = new EducationalObjectiveImpl();
		return educationalObjective;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LearningActivity createLearningActivity() {
		LearningActivityImpl learningActivity = new LearningActivityImpl();
		return learningActivity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivityObjective createActivityObjective() {
		ActivityObjectiveImpl activityObjective = new ActivityObjectiveImpl();
		return activityObjective;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StartNode createStartNode() {
		StartNodeImpl startNode = new StartNodeImpl();
		return startNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndNode createEndNode() {
		EndNodeImpl endNode = new EndNodeImpl();
		return endNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EomlPackage getEomlPackage() {
		return (EomlPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EomlPackage getPackage() {
		return EomlPackage.eINSTANCE;
	}

} //EomlFactoryImpl
