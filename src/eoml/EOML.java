/**
 */
package eoml;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EOML</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eoml.EOML#getEntities <em>Entities</em>}</li>
 * </ul>
 *
 * @see eoml.EomlPackage#getEOML()
 * @model
 * @generated
 */
public interface EOML extends EObject {
	/**
	 * Returns the value of the '<em><b>Entities</b></em>' containment reference list.
	 * The list contents are of type {@link eoml.Entity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entities</em>' containment reference list.
	 * @see eoml.EomlPackage#getEOML_Entities()
	 * @model containment="true"
	 * @generated
	 */
	EList<Entity> getEntities();

} // EOML
