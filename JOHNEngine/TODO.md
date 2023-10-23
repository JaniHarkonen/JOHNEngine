## TODO

- handle scrolling in Input
???? get information about the monitor from window
??? should ABasicWindowRequests be implemented using lambdas instead???
- remove cringe
???? new exception class??
- ABufferedRequestManager should have a template
- MAJOR: instead of spawning threads each time there are new load requests,
AssetRequestManager should spawn the threads once and then distribute the 
work in real time

### Renderer structure

=== setting up the shader program
x create shader program
- declare uniforms
x create shaders
	xx create
	xx set source
	xx compile
	xx attach to shader program x
x link shader program
x deload shaders
	xx detach
	xx delete

=== rendering
- bind shader program

=== disposing
- unbind shader program
- delete shader program