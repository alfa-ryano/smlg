/**
 */
package myPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see myPackage.MyPackageFactory
 * @model kind="package"
 * @generated
 */
public interface MyPackagePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "myPackage";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "myUri";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "myPrefix";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MyPackagePackage eINSTANCE = myPackage.impl.MyPackagePackageImpl.init();

	/**
	 * The meta object id for the '{@link myPackage.impl.FilesystemImpl <em>Filesystem</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see myPackage.impl.FilesystemImpl
	 * @see myPackage.impl.MyPackagePackageImpl#getFilesystem()
	 * @generated
	 */
	int FILESYSTEM = 0;

	/**
	 * The feature id for the '<em><b>Drives</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILESYSTEM__DRIVES = 0;

	/**
	 * The feature id for the '<em><b>Syncs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILESYSTEM__SYNCS = 1;

	/**
	 * The number of structural features of the '<em>Filesystem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILESYSTEM_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link myPackage.impl.FileImpl <em>File</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see myPackage.impl.FileImpl
	 * @see myPackage.impl.MyPackagePackageImpl#getFile()
	 * @generated
	 */
	int FILE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__NAME = 0;

	/**
	 * The number of structural features of the '<em>File</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link myPackage.impl.FolderImpl <em>Folder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see myPackage.impl.FolderImpl
	 * @see myPackage.impl.MyPackagePackageImpl#getFolder()
	 * @generated
	 */
	int FOLDER = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__NAME = FILE__NAME;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__CONTENTS = FILE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER_FEATURE_COUNT = FILE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link myPackage.impl.DriveImpl <em>Drive</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see myPackage.impl.DriveImpl
	 * @see myPackage.impl.MyPackagePackageImpl#getDrive()
	 * @generated
	 */
	int DRIVE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVE__NAME = FOLDER__NAME;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVE__CONTENTS = FOLDER__CONTENTS;

	/**
	 * The number of structural features of the '<em>Drive</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRIVE_FEATURE_COUNT = FOLDER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link myPackage.impl.ShortcutImpl <em>Shortcut</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see myPackage.impl.ShortcutImpl
	 * @see myPackage.impl.MyPackagePackageImpl#getShortcut()
	 * @generated
	 */
	int SHORTCUT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHORTCUT__NAME = FILE__NAME;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHORTCUT__TARGET = FILE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Shortcut</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHORTCUT_FEATURE_COUNT = FILE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link myPackage.impl.SyncImpl <em>Sync</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see myPackage.impl.SyncImpl
	 * @see myPackage.impl.MyPackagePackageImpl#getSync()
	 * @generated
	 */
	int SYNC = 4;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNC__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNC__TARGET = 1;

	/**
	 * The number of structural features of the '<em>Sync</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNC_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link myPackage.Filesystem <em>Filesystem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Filesystem</em>'.
	 * @see myPackage.Filesystem
	 * @generated
	 */
	EClass getFilesystem();

	/**
	 * Returns the meta object for the containment reference list '{@link myPackage.Filesystem#getDrives <em>Drives</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Drives</em>'.
	 * @see myPackage.Filesystem#getDrives()
	 * @see #getFilesystem()
	 * @generated
	 */
	EReference getFilesystem_Drives();

	/**
	 * Returns the meta object for the containment reference list '{@link myPackage.Filesystem#getSyncs <em>Syncs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Syncs</em>'.
	 * @see myPackage.Filesystem#getSyncs()
	 * @see #getFilesystem()
	 * @generated
	 */
	EReference getFilesystem_Syncs();

	/**
	 * Returns the meta object for class '{@link myPackage.Drive <em>Drive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Drive</em>'.
	 * @see myPackage.Drive
	 * @generated
	 */
	EClass getDrive();

	/**
	 * Returns the meta object for class '{@link myPackage.Folder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Folder</em>'.
	 * @see myPackage.Folder
	 * @generated
	 */
	EClass getFolder();

	/**
	 * Returns the meta object for the containment reference list '{@link myPackage.Folder#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see myPackage.Folder#getContents()
	 * @see #getFolder()
	 * @generated
	 */
	EReference getFolder_Contents();

	/**
	 * Returns the meta object for class '{@link myPackage.Shortcut <em>Shortcut</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shortcut</em>'.
	 * @see myPackage.Shortcut
	 * @generated
	 */
	EClass getShortcut();

	/**
	 * Returns the meta object for the reference '{@link myPackage.Shortcut#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see myPackage.Shortcut#getTarget()
	 * @see #getShortcut()
	 * @generated
	 */
	EReference getShortcut_Target();

	/**
	 * Returns the meta object for class '{@link myPackage.Sync <em>Sync</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sync</em>'.
	 * @see myPackage.Sync
	 * @generated
	 */
	EClass getSync();

	/**
	 * Returns the meta object for the reference '{@link myPackage.Sync#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see myPackage.Sync#getSource()
	 * @see #getSync()
	 * @generated
	 */
	EReference getSync_Source();

	/**
	 * Returns the meta object for the reference '{@link myPackage.Sync#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see myPackage.Sync#getTarget()
	 * @see #getSync()
	 * @generated
	 */
	EReference getSync_Target();

	/**
	 * Returns the meta object for class '{@link myPackage.File <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File</em>'.
	 * @see myPackage.File
	 * @generated
	 */
	EClass getFile();

	/**
	 * Returns the meta object for the attribute '{@link myPackage.File#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see myPackage.File#getName()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_Name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MyPackageFactory getMyPackageFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link myPackage.impl.FilesystemImpl <em>Filesystem</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see myPackage.impl.FilesystemImpl
		 * @see myPackage.impl.MyPackagePackageImpl#getFilesystem()
		 * @generated
		 */
		EClass FILESYSTEM = eINSTANCE.getFilesystem();

		/**
		 * The meta object literal for the '<em><b>Drives</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILESYSTEM__DRIVES = eINSTANCE.getFilesystem_Drives();

		/**
		 * The meta object literal for the '<em><b>Syncs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILESYSTEM__SYNCS = eINSTANCE.getFilesystem_Syncs();

		/**
		 * The meta object literal for the '{@link myPackage.impl.DriveImpl <em>Drive</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see myPackage.impl.DriveImpl
		 * @see myPackage.impl.MyPackagePackageImpl#getDrive()
		 * @generated
		 */
		EClass DRIVE = eINSTANCE.getDrive();

		/**
		 * The meta object literal for the '{@link myPackage.impl.FolderImpl <em>Folder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see myPackage.impl.FolderImpl
		 * @see myPackage.impl.MyPackagePackageImpl#getFolder()
		 * @generated
		 */
		EClass FOLDER = eINSTANCE.getFolder();

		/**
		 * The meta object literal for the '<em><b>Contents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FOLDER__CONTENTS = eINSTANCE.getFolder_Contents();

		/**
		 * The meta object literal for the '{@link myPackage.impl.ShortcutImpl <em>Shortcut</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see myPackage.impl.ShortcutImpl
		 * @see myPackage.impl.MyPackagePackageImpl#getShortcut()
		 * @generated
		 */
		EClass SHORTCUT = eINSTANCE.getShortcut();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHORTCUT__TARGET = eINSTANCE.getShortcut_Target();

		/**
		 * The meta object literal for the '{@link myPackage.impl.SyncImpl <em>Sync</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see myPackage.impl.SyncImpl
		 * @see myPackage.impl.MyPackagePackageImpl#getSync()
		 * @generated
		 */
		EClass SYNC = eINSTANCE.getSync();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYNC__SOURCE = eINSTANCE.getSync_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYNC__TARGET = eINSTANCE.getSync_Target();

		/**
		 * The meta object literal for the '{@link myPackage.impl.FileImpl <em>File</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see myPackage.impl.FileImpl
		 * @see myPackage.impl.MyPackagePackageImpl#getFile()
		 * @generated
		 */
		EClass FILE = eINSTANCE.getFile();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__NAME = eINSTANCE.getFile_Name();

	}

} //MyPackagePackage
