## TODO
- NEXT
	-- iterator of InstanceManager doesn't work
	-- figure out instance deletion

- handle scrolling in Input
???? get information about the monitor from window
??? should ABasicWindowRequests be implemented using lambdas instead???
- remove cringe
???? new exception class??
- ABufferedRequestManager should have a template
- MAJOR: instead of spawning threads each time there are new load requests,
AssetRequestManager should spawn the threads once and then distribute the 
work in real time
- consider if game instance needs to be stored in AGame's static field
- some abstract classes should be declared within the "core"-package
	-- "core" should only contain the basic "scaffolding" for the components
	of the game engine as well as the basic engine components without which
	the engine cannot run
- BIG CHANGE??: AGame/ARenderer/JWorld should be passed in as tick()/render()
is called to avoid extra memory usage

### Renderer structure

=== setting up the shader program
x create shader program
- declare uniforms
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