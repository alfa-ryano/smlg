/**
 */
package eoml;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Learning Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eoml.LearningActivity#getObjectives <em>Objectives</em>}</li>
 * </ul>
 *
 * @see eoml.EomlPackage#getLearningActivity()
 * @model annotation="gmf.node label='name' style='html=1;whiteSpace=wrap;rounded=1;'"
 * @generated
 */
public interface LearningActivity extends Node {
	/**
	 * Returns the value of the '<em><b>Objectives</b></em>' reference list.
	 * The list contents are of type {@link eoml.ActivityObjective}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Objectives</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Objectives</em>' reference list.
	 * @see eoml.EomlPackage#getLearningActivity_Objectives()
	 * @model
	 * @generated
	 */
	EList<ActivityObjective> getObjectives();

} // LearningActivity
