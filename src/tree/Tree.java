/**
 */
package tree;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tree</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tree.Tree#getChildren <em>Children</em>}</li>
 *   <li>{@link tree.Tree#getParentValSingle <em>Parent Val Single</em>}</li>
 *   <li>{@link tree.Tree#getParentComparment <em>Parent Comparment</em>}</li>
 *   <li>{@link tree.Tree#getParent <em>Parent</em>}</li>
 *   <li>{@link tree.Tree#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see tree.TreePackage#getTree()
 * @model
 * @generated
 */
public interface Tree extends EObject {
	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link tree.Node}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see tree.TreePackage#getTree_Children()
	 * @model containment="true"
	 * @generated
	 */
	EList<Node> getChildren();

	/**
	 * Returns the value of the '<em><b>Parent Val Single</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Val Single</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Val Single</em>' containment reference.
	 * @see #setParentValSingle(Node)
	 * @see tree.TreePackage#getTree_ParentValSingle()
	 * @model containment="true"
	 * @generated
	 */
	Node getParentValSingle();

	/**
	 * Sets the value of the '{@link tree.Tree#getParentValSingle <em>Parent Val Single</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Val Single</em>' containment reference.
	 * @see #getParentValSingle()
	 * @generated
	 */
	void setParentValSingle(Node value);

	/**
	 * Returns the value of the '<em><b>Parent Comparment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Comparment</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Comparment</em>' containment reference.
	 * @see #setParentComparment(Node)
	 * @see tree.TreePackage#getTree_ParentComparment()
	 * @model containment="true"
	 * @generated
	 */
	Node getParentComparment();

	/**
	 * Sets the value of the '{@link tree.Tree#getParentComparment <em>Parent Comparment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Comparment</em>' containment reference.
	 * @see #getParentComparment()
	 * @generated
	 */
	void setParentComparment(Node value);

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(Node)
	 * @see tree.TreePackage#getTree_Parent()
	 * @model
	 * @generated
	 */
	Node getParent();

	/**
	 * Sets the value of the '{@link tree.Tree#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Node value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>"1234"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see tree.TreePackage#getTree_Name()
	 * @model default="1234"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link tree.Tree#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Tree
