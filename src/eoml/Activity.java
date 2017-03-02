/**
 */
package eoml;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eoml.Activity#getMetamodel <em>Metamodel</em>}</li>
 *   <li>{@link eoml.Activity#getObjectives <em>Objectives</em>}</li>
 * </ul>
 *
 * @see eoml.EomlPackage#getActivity()
 * @model annotation="gmf.node mxLabel='name' mxShape='swimlane' mxChildLayout='stackLayout' mxCollapsible='1' mxHorizontalStack='0' mxResizeParent='0' mxResizeLast='1' mxRounded='1' mxMarginBottom='5' mxMarginLeft='5' mxMarginRight='5' mxMarginTop='5' mxHtml='1' mxWhiteSpace='wrap' mxWidth='200' mxHeight='120'"
 * @generated
 */
public interface Activity extends Node {
	/**
	 * Returns the value of the '<em><b>Metamodel</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metamodel</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metamodel</em>' attribute.
	 * @see #setMetamodel(String)
	 * @see eoml.EomlPackage#getActivity_Metamodel()
	 * @model default=""
	 * @generated
	 */
	String getMetamodel();

	/**
	 * Sets the value of the '{@link eoml.Activity#getMetamodel <em>Metamodel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metamodel</em>' attribute.
	 * @see #getMetamodel()
	 * @generated
	 */
	void setMetamodel(String value);

	/**
	 * Returns the value of the '<em><b>Objectives</b></em>' containment reference list.
	 * The list contents are of type {@link eoml.Objective}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Objectives</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Objectives</em>' containment reference list.
	 * @see eoml.EomlPackage#getActivity_Objectives()
	 * @model containment="true"
	 *        annotation="gmf.compartment mxShape='swimlane' mxCollapsible='0' mxNoLabel='1' xEditable='0' mxFillColor='none' mxStrokeColor='none' mxStartSize='0'"
	 * @generated
	 */
	EList<Objective> getObjectives();

} // Activity
