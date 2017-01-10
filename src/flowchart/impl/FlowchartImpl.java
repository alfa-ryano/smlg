/**
 */
package flowchart.impl;

import flowchart.Flowchart;
import flowchart.FlowchartPackage;
import flowchart.Node;
import flowchart.Transition;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Flowchart</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link flowchart.impl.FlowchartImpl#getNodes2 <em>Nodes2</em>}</li>
 *   <li>{@link flowchart.impl.FlowchartImpl#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link flowchart.impl.FlowchartImpl#getNodes1 <em>Nodes1</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FlowchartImpl extends EObjectImpl implements Flowchart {
	/**
	 * The cached value of the '{@link #getNodes2() <em>Nodes2</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes2()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> nodes2;

	/**
	 * The cached value of the '{@link #getTransitions() <em>Transitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> transitions;

	/**
	 * The cached value of the '{@link #getNodes1() <em>Nodes1</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes1()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> nodes1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FlowchartImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FlowchartPackage.Literals.FLOWCHART;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getNodes1() {
		if (nodes1 == null) {
			nodes1 = new EObjectContainmentEList<Node>(Node.class, this, FlowchartPackage.FLOWCHART__NODES1);
		}
		return nodes1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getNodes2() {
		if (nodes2 == null) {
			nodes2 = new EObjectContainmentEList<Node>(Node.class, this, FlowchartPackage.FLOWCHART__NODES2);
		}
		return nodes2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getTransitions() {
		if (transitions == null) {
			transitions = new EObjectContainmentEList<Transition>(Transition.class, this, FlowchartPackage.FLOWCHART__TRANSITIONS);
		}
		return transitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FlowchartPackage.FLOWCHART__NODES2:
				return ((InternalEList<?>)getNodes2()).basicRemove(otherEnd, msgs);
			case FlowchartPackage.FLOWCHART__TRANSITIONS:
				return ((InternalEList<?>)getTransitions()).basicRemove(otherEnd, msgs);
			case FlowchartPackage.FLOWCHART__NODES1:
				return ((InternalEList<?>)getNodes1()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FlowchartPackage.FLOWCHART__NODES2:
				return getNodes2();
			case FlowchartPackage.FLOWCHART__TRANSITIONS:
				return getTransitions();
			case FlowchartPackage.FLOWCHART__NODES1:
				return getNodes1();
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
			case FlowchartPackage.FLOWCHART__NODES2:
				getNodes2().clear();
				getNodes2().addAll((Collection<? extends Node>)newValue);
				return;
			case FlowchartPackage.FLOWCHART__TRANSITIONS:
				getTransitions().clear();
				getTransitions().addAll((Collection<? extends Transition>)newValue);
				return;
			case FlowchartPackage.FLOWCHART__NODES1:
				getNodes1().clear();
				getNodes1().addAll((Collection<? extends Node>)newValue);
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
			case FlowchartPackage.FLOWCHART__NODES2:
				getNodes2().clear();
				return;
			case FlowchartPackage.FLOWCHART__TRANSITIONS:
				getTransitions().clear();
				return;
			case FlowchartPackage.FLOWCHART__NODES1:
				getNodes1().clear();
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
			case FlowchartPackage.FLOWCHART__NODES2:
				return nodes2 != null && !nodes2.isEmpty();
			case FlowchartPackage.FLOWCHART__TRANSITIONS:
				return transitions != null && !transitions.isEmpty();
			case FlowchartPackage.FLOWCHART__NODES1:
				return nodes1 != null && !nodes1.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //FlowchartImpl
