/**
 */
package statechart;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link statechart.State#getSubstates <em>Substates</em>}</li>
 * </ul>
 *
 * @see statechart.StatechartPackage#getState()
 * @model annotation="gmf.node mxLabel='name' mxShape='swimlane' mxChildLayout='stackLayout' mxCollapsible='1' mxHorizontalStack='0' mxResizeParent='0' mxResizeLast='1' mxRounded='1' mxMarginBottom='7' mxMarginLeft='7' mxMarginRight='7' mxMarginTop='7' mxHtml='1' mxWhiteSpace='wrap' mxWidth='200' mxHeight='120' mxSwimlaneFillColor='#FF0000'"
 * @generated
 */
public interface State extends Node {
	/**
	 * Returns the value of the '<em><b>Substates</b></em>' containment reference list.
	 * The list contents are of type {@link statechart.State}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Substates</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Substates</em>' containment reference list.
	 * @see statechart.StatechartPackage#getState_Substates()
	 * @model containment="true"
	 *        annotation="gmf.compartment mxShape='swimlane' mxCollapsible='0' mxNoLabel='1' xEditable='0' mxStrokeColor='none' mxStartSize='0'"
	 * @generated
	 */
	EList<State> getSubstates();

} // State
