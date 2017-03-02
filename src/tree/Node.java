/**
 */
package tree;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tree.Node#getChildren <em>Children</em>}</li>
 *   <li>{@link tree.Node#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see tree.TreePackage#getNode()
 * @model annotation="gmf.node mxLabel='name' mxFill='1' mxHtml='1' mxFillColor='none' mxShape='swimlane' mxChildLayout='stackLayout' mxCollapsible='1' mxHorizontalStack='0' mxResizeParent='0' mxResizeLast='1' mxRounded='1' mxMarginBottom='0' mxMarginLeft='0' mxMarginRight='0' mxMarginTop='0' mxWhiteSpace='wrap' mxWidth='200' mxHeight='120'"
 * @generated
 */
public interface Node extends EObject {

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
	 * @see tree.TreePackage#getNode_Children()
	 * @model containment="true"
	 *        annotation="gmf.compartment mxShape='swimlane' mxCollapsible='0' mxNoLabel='1' xEditable='0' mxFillColor='none' mxStrokeColor='none'"
	 * @generated
	 */
	EList<Node> getChildren();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>"Node"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see tree.TreePackage#getNode_Name()
	 * @model default="Node"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link tree.Node#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);
} // Node
