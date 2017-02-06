/**
 */
package filesystem;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Folder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link filesystem.Folder#getContents <em>Contents</em>}</li>
 * </ul>
 *
 * @see filesystem.FilesystemPackage#getFolder()
 * @model annotation="gmf.node style='swimlane;fill=1;html=1;childLayout=stackLayout;fillColor=none;horizontalStack=0;resizeParent=1;resizeLast=1;collapsible=1;marginBottom=0;swimlaneFillColor=#ffffff;'"
 * @generated
 */
public interface Folder extends File {
	/**
	 * Returns the value of the '<em><b>Contents</b></em>' containment reference list.
	 * The list contents are of type {@link filesystem.File}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contents</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contents</em>' containment reference list.
	 * @see filesystem.FilesystemPackage#getFolder_Contents()
	 * @model containment="true"
	 * @generated
	 */
	EList<File> getContents();

} // Folder
