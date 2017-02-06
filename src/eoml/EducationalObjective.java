/**
 */
package eoml;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Educational Objective</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eoml.EducationalObjective#getEntities <em>Entities</em>}</li>
 * </ul>
 *
 * @see eoml.EomlPackage#getEducationalObjective()
 * @model annotation="gmf.node label='name' style='html=1;whiteSpace=wrap;rounded=1;'"
 * @generated
 */
public interface EducationalObjective extends Node {
	/**
	 * Returns the value of the '<em><b>Entities</b></em>' reference list.
	 * The list contents are of type {@link eoml.Entity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entities</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entities</em>' reference list.
	 * @see eoml.EomlPackage#getEducationalObjective_Entities()
	 * @model
	 * @generated
	 */
	EList<Entity> getEntities();

} // EducationalObjective
