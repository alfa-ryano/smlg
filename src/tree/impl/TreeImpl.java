/**
 */
package tree.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import tree.Node;
import tree.Tree;
import tree.TreePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tree</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tree.impl.TreeImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link tree.impl.TreeImpl#getParentValSingle <em>Parent Val Single</em>}</li>
 *   <li>{@link tree.impl.TreeImpl#getParentComparment <em>Parent Comparment</em>}</li>
 *   <li>{@link tree.impl.TreeImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link tree.impl.TreeImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TreeImpl extends EObjectImpl implements Tree {
	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> children;

	/**
	 * The cached value of the '{@link #getParentValSingle() <em>Parent Val Single</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentValSingle()
	 * @generated
	 * @ordered
	 */
	protected Node parentValSingle;

	/**
	 * The cached value of the '{@link #getParentComparment() <em>Parent Comparment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentComparment()
	 * @generated
	 * @ordered
	 */
	protected Node parentComparment;

	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected Node parent;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "1234";

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TreeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TreePackage.Literals.TREE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList<Node>(Node.class, this, TreePackage.TREE__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getParentValSingle() {
		return parentValSingle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentValSingle(Node newParentValSingle, NotificationChain msgs) {
		Node oldParentValSingle = parentValSingle;
		parentValSingle = newParentValSingle;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TreePackage.TREE__PARENT_VAL_SINGLE, oldParentValSingle, newParentValSingle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentValSingle(Node newParentValSingle) {
		if (newParentValSingle != parentValSingle) {
			NotificationChain msgs = null;
			if (parentValSingle != null)
				msgs = ((InternalEObject)parentValSingle).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TreePackage.TREE__PARENT_VAL_SINGLE, null, msgs);
			if (newParentValSingle != null)
				msgs = ((InternalEObject)newParentValSingle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TreePackage.TREE__PARENT_VAL_SINGLE, null, msgs);
			msgs = basicSetParentValSingle(newParentValSingle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE__PARENT_VAL_SINGLE, newParentValSingle, newParentValSingle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getParentComparment() {
		return parentComparment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentComparment(Node newParentComparment, NotificationChain msgs) {
		Node oldParentComparment = parentComparment;
		parentComparment = newParentComparment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TreePackage.TREE__PARENT_COMPARMENT, oldParentComparment, newParentComparment);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentComparment(Node newParentComparment) {
		if (newParentComparment != parentComparment) {
			NotificationChain msgs = null;
			if (parentComparment != null)
				msgs = ((InternalEObject)parentComparment).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TreePackage.TREE__PARENT_COMPARMENT, null, msgs);
			if (newParentComparment != null)
				msgs = ((InternalEObject)newParentComparment).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TreePackage.TREE__PARENT_COMPARMENT, null, msgs);
			msgs = basicSetParentComparment(newParentComparment, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE__PARENT_COMPARMENT, newParentComparment, newParentComparment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (Node)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TreePackage.TREE__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(Node newParent) {
		Node oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TreePackage.TREE__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case TreePackage.TREE__PARENT_VAL_SINGLE:
				return basicSetParentValSingle(null, msgs);
			case TreePackage.TREE__PARENT_COMPARMENT:
				return basicSetParentComparment(null, msgs);
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
			case TreePackage.TREE__CHILDREN:
				return getChildren();
			case TreePackage.TREE__PARENT_VAL_SINGLE:
				return getParentValSingle();
			case TreePackage.TREE__PARENT_COMPARMENT:
				return getParentComparment();
			case TreePackage.TREE__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case TreePackage.TREE__NAME:
				return getName();
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
			case TreePackage.TREE__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends Node>)newValue);
				return;
			case TreePackage.TREE__PARENT_VAL_SINGLE:
				setParentValSingle((Node)newValue);
				return;
			case TreePackage.TREE__PARENT_COMPARMENT:
				setParentComparment((Node)newValue);
				return;
			case TreePackage.TREE__PARENT:
				setParent((Node)newValue);
				return;
			case TreePackage.TREE__NAME:
				setName((String)newValue);
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
			case TreePackage.TREE__CHILDREN:
				getChildren().clear();
				return;
			case TreePackage.TREE__PARENT_VAL_SINGLE:
				setParentValSingle((Node)null);
				return;
			case TreePackage.TREE__PARENT_COMPARMENT:
				setParentComparment((Node)null);
				return;
			case TreePackage.TREE__PARENT:
				setParent((Node)null);
				return;
			case TreePackage.TREE__NAME:
				setName(NAME_EDEFAULT);
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
			case TreePackage.TREE__CHILDREN:
				return children != null && !children.isEmpty();
			case TreePackage.TREE__PARENT_VAL_SINGLE:
				return parentValSingle != null;
			case TreePackage.TREE__PARENT_COMPARMENT:
				return parentComparment != null;
			case TreePackage.TREE__PARENT:
				return parent != null;
			case TreePackage.TREE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //TreeImpl
