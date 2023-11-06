## TODO
- handle scrolling in Input
???? get information about the monitor from window
??? should ABasicWindowRequests be implemented using lambdas instead???
- remove cringe
???? new exception class??
- MAJOR: instead of spawning threads each time there are new load requests,
AssetRequestManager should spawn the threads once and then distribute the 
work in real time
- some abstract classes should be declared within the "core"-package
	-- "core" should only contain the basic "scaffolding" for the components
	of the game engine as well as the basic engine components without which
	the engine cannot run
- uniforms should not have templates?
- consider what renderer functionalities can be defined in ARenderer
- AGame may not need getters for its components
	-- these should be removed if possible as it ruins the modularity of 
	having opt-in components
x consider if there should be a functionality for marking AAssets as "preloaded"
and/or to make AAssets persistent (non-deloadable)
- requests should always be in the same package as the class using them
	! NOTICE: core.assetmngr.reqs contains the AssetRequestManager which uses
	the requests contained in the same package, AssetManager does not use these
	requests
- maybe AssetManager should also hold a map of AAssetLoaders
- add disposing/destruction methods to all relevant classes
- create getDefault()-methods for ARenderAssets
- Mesh.populateMeshWithAIMesh() may be able to initialize an array using the 
Buffer.sizeof() with Buffer.limit to calculate the number of elements inside
a buffer
	-> doing this allows us to bypass the creation and "arrayfication" of an
	ArrayList
	-> NOTICEEEE!: Actually you can just use mNumVertices to get the buffer
	"size"
- rethink asset system again to get rid of all the casting
	-> currently you have to cast each time you call AssetManager.getAsset()
- it probably makes more sense to have a UUID-class that has static methods for
	obtaining instance IDs so that all AGameObjects will instantly have a 
	unique ID without the risk of the developer forgetting to set it
	-> this way weird setter methods that may be suseptible to inter-package 
	visiblity errors can be avoided as well
- WARNING!: core.renderer.shdprog.Shader imports assets from "basic" package
THIS IS NOT ALLOWED -> REFACTOR
	
### New asset structure
v Each asset should have a loader class
	-> this is because the loading process may (and does often) produce more 
	than a single asset
	-> loaders will be sent to the AssetManager when issuing requests
	-- loading
		o option1: loaders can be used to poll assets once the loading process has 
		completed
		o option2: assets created by loaders will be immediately declared in the
		Asset Manager
		o option3: assets that are to be created will be passed onto the loader and
		the structure of the loader fills in the data
		v option4: option3 + option1 (both can be implemented in this architecture) 
	? impact on AssetGroups
		?? how will the groups be loaded now
		?? what about deloading
- because AssetManager is to be seen as a central hub for all assets used in the
game, even the assets that are to be "loaded" by other engine components must 
be declared in the AssetManager (for example, assets "loaded" by OpenGL)
	-> some assets are specific to engine components, such as the ARendererAssets,
	however, they must still be declared in the AssetManager as AGameObjects will
	also reference these assets
	-> engine components should still be kept separated to ensure modularity, for
	example, Renderer3D must NOT reference AssetManager, even though in most cases
	it uses the assets loaded in by the AssetManager when rendering

### Renderer structure

=== importing loaded AAssets to OpenGL
v create ARenderableAsset
v create a SceneObject-asset and pass it ARenderableAssets that are expected
to be extracted from the AIScene
- create a RendererCache which will hold the AAssets that are to be loaded
using OpenGL-methods once the AssetManager has loaded them
x option #1 : manual loading
	-- figure out a system to send requests to the Window-component when 
	Assimp is done loading in the asset
	-> DOWNSIDE: AssetManager must first handle SceneObject loading request
	and then somehow notify Window when the loading has finished
	-> DOWNSIDE: may require architectural changes to the way that ARequests
	are handled (each "request" may need a "response")
	-> UPSIDE: more control over loading because assets will be loaded in 
	manually in the order that the requests are sent to the Window
	-> UPSIDE: the time at which the requests are sent to the Window can be
	decided so that all loading occurs within a certain timeframe, which causes
	all lag caused by the loading to be experienced at once
v option #2 : load-when-needed approach 
	-- upon rendering, check if an asset has been loaded in, and if not, load it
	-> UPSIDE: loading assets will be more spread out as it is only in the worst 
	case scenario that assets will end up being loaded at the same time; this 
	reduces lag
	-> UPSIDE: requires no changes to the current architecture
	-> DOWNSIDE: little to no control over when the assets are loaded as this is
	determined automatically 
	-> DOWNSIDE: MAY lead to FPS drops mid-game when costly loading operations are 
	carried out (the validity of this argument is difficult to determine before 
	actual testing with complex renderings)

=== setting up the shader program
x create shader program
x declare uniforms
x create shaders
	xx create
	xx set source
	xx compile
	xx attach to shader program
x link shader program
x deload shaders
	xx detach
	xx delete

=== rendering
x bind shader program

=== disposing
x unbind shader program
x delete shader program
