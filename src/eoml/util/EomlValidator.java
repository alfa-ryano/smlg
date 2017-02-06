/**
 */
package eoml.util;

import eoml.*;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see eoml.EomlPackage
 * @generated
 */
public class EomlValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EomlValidator INSTANCE = new EomlValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "eoml";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EomlValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return EomlPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case EomlPackage.EOML:
				return validateEOML((EOML)value, diagnostics, context);
			case EomlPackage.ENTITY:
				return validateEntity((Entity)value, diagnostics, context);
			case EomlPackage.NODE:
				return validateNode((Node)value, diagnostics, context);
			case EomlPackage.TRANSITION:
				return validateTransition((Transition)value, diagnostics, context);
			case EomlPackage.EDUCATIONAL_OBJECTIVE:
				return validateEducationalObjective((EducationalObjective)value, diagnostics, context);
			case EomlPackage.LEARNING_ACTIVITY:
				return validateLearningActivity((LearningActivity)value, diagnostics, context);
			case EomlPackage.ACTIVITY_OBJECTIVE:
				return validateActivityObjective((ActivityObjective)value, diagnostics, context);
			case EomlPackage.START_NODE:
				return validateStartNode((StartNode)value, diagnostics, context);
			case EomlPackage.END_NODE:
				return validateEndNode((EndNode)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEOML(EOML eoml, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(eoml, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEntity(Entity entity, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(entity, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNode(Node node, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(node, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransition(Transition transition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(transition, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEducationalObjective(EducationalObjective educationalObjective, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(educationalObjective, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLearningActivity(LearningActivity learningActivity, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(learningActivity, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateActivityObjective(ActivityObjective activityObjective, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(activityObjective, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStartNode(StartNode startNode, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(startNode, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(startNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(startNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(startNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(startNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(startNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(startNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(startNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(startNode, diagnostics, context);
		if (result || diagnostics != null) result &= validateStartNode_constraintNoIncoming(startNode, diagnostics, context);
		return result;
	}

	/**
	 * Validates the constraintNoIncoming constraint of '<em>Start Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStartNode_constraintNoIncoming(StartNode startNode, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "constraintNoIncoming", getObjectLabel(startNode, context) },
						 new Object[] { startNode },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEndNode(EndNode endNode, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(endNode, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(endNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(endNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(endNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(endNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(endNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(endNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(endNode, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(endNode, diagnostics, context);
		if (result || diagnostics != null) result &= validateEndNode_constraintNoOutgoing(endNode, diagnostics, context);
		return result;
	}

	/**
	 * Validates the constraintNoOutgoing constraint of '<em>End Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEndNode_constraintNoOutgoing(EndNode endNode, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO implement the constraint
		// -> specify the condition that violates the constraint
		// -> verify the diagnostic details, including severity, code, and message
		// Ensure that you remove @generated or mark it @generated NOT
		if (false) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "constraintNoOutgoing", getObjectLabel(endNode, context) },
						 new Object[] { endNode },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //EomlValidator
