/**
 */
package filesystem;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sync</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link filesystem.Sync#getSource <em>Source</em>}</li>
 *   <li>{@link filesystem.Sync#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see filesystem.FilesystemPackage#getSync()
 * @model annotation="gmf.link source='source' target='target' style='dashed=1;dashPattern=1 4;strokeWidth=2;endArrow=none;'"
 * @generated
 */
public interface Sync extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(File)
	 * @see filesystem.FilesystemPackage#getSync_Source()
	 * @model
	 * @generated
	 */
	File getSource();

	/**
	 * Sets the value of the '{@link filesystem.Sync#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(File value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(File)
	 * @see filesystem.FilesystemPackage#getSync_Target()
	 * @model
	 * @generated
	 */
	File getTarget();

	/**
	 * Sets the value of the '{@link filesystem.Sync#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(File value);

} // Sync
