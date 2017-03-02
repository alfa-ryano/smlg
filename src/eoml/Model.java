/**
 */
package eoml;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eoml.Model#getMetamodel <em>Metamodel</em>}</li>
 *   <li>{@link eoml.Model#getSourceModel <em>Source Model</em>}</li>
 * </ul>
 *
 * @see eoml.EomlPackage#getModel()
 * @model annotation="gmf.node mxLabel='name' mxShape='rectangle' mxHtml='1' mxWhiteSpace='wrap' mxWidth='40' mxHeight='50'"
 * @generated
 */
public interface Model extends Node {

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
	 * @see eoml.EomlPackage#getModel_Metamodel()
	 * @model default=""
	 * @generated
	 */
	String getMetamodel();

	/**
	 * Sets the value of the '{@link eoml.Model#getMetamodel <em>Metamodel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metamodel</em>' attribute.
	 * @see #getMetamodel()
	 * @generated
	 */
	void setMetamodel(String value);

	/**
	 * Returns the value of the '<em><b>Source Model</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Model</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Model</em>' attribute.
	 * @see #setSourceModel(String)
	 * @see eoml.EomlPackage#getModel_SourceModel()
	 * @model default=""
	 * @generated
	 */
	String getSourceModel();

	/**
	 * Sets the value of the '{@link eoml.Model#getSourceModel <em>Source Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Model</em>' attribute.
	 * @see #getSourceModel()
	 * @generated
	 */
	void setSourceModel(String value);
} // Model
