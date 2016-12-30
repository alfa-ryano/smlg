/**
 */
package myPackage;

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
 *   <li>{@link myPackage.Sync#getSource <em>Source</em>}</li>
 *   <li>{@link myPackage.Sync#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see myPackage.MyPackagePackage#getSync()
 * @model annotation="gmf.link source='source' target='target' style='dot' width='2'"
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
	 * @see myPackage.MyPackagePackage#getSync_Source()
	 * @model
	 * @generated
	 */
	File getSource();

	/**
	 * Sets the value of the '{@link myPackage.Sync#getSource <em>Source</em>}' reference.
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
	 * @see myPackage.MyPackagePackage#getSync_Target()
	 * @model
	 * @generated
	 */
	File getTarget();

	/**
	 * Sets the value of the '{@link myPackage.Sync#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(File value);

} // Sync
