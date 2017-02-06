/**
 */
package eoml.impl;

import eoml.ActivityObjective;
import eoml.EomlPackage;
import eoml.LearningActivity;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Learning Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eoml.impl.LearningActivityImpl#getObjectives <em>Objectives</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LearningActivityImpl extends NodeImpl implements LearningActivity {
	/**
	 * The cached value of the '{@link #getObjectives() <em>Objectives</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectives()
	 * @generated
	 * @ordered
	 */
	protected EList<ActivityObjective> objectives;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LearningActivityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EomlPackage.Literals.LEARNING_ACTIVITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ActivityObjective> getObjectives() {
		if (objectives == null) {
			objectives = new EObjectResolvingEList<ActivityObjective>(ActivityObjective.class, this, EomlPackage.LEARNING_ACTIVITY__OBJECTIVES);
		}
		return objectives;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EomlPackage.LEARNING_ACTIVITY__OBJECTIVES:
				return getObjectives();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EomlPackage.LEARNING_ACTIVITY__OBJECTIVES:
				getObjectives().clear();
				getObjectives().addAll((Collection<? extends ActivityObjective>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case EomlPackage.LEARNING_ACTIVITY__OBJECTIVES:
				getObjectives().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case EomlPackage.LEARNING_ACTIVITY__OBJECTIVES:
				return objectives != null && !objectives.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //LearningActivityImpl
