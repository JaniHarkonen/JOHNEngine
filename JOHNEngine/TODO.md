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
- consider if there should be a functionality for marking AAssets as "preloaded"
and/or to make AAssets persistent (non-deloadable)
- requests should always be in the same package as the class using them
	-> for example, core.assetmngr.reqs.* should be in core.assetmngr instead

### Renderer structure

=== importing loaded AAssets to OpenGL
? ARequests should also hold a AResponse which can be used to track
the state of the request 
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
