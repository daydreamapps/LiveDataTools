# LiveDataTools
[ ![Download](https://api.bintray.com/packages/daydreamapplications/livedata/tools/images/download.svg?version=1.0.1) ](https://bintray.com/daydreamapplications/livedata/tools/1.0.1/link)
![Jenkins Status](http://jenkins.daydreamapplications.com/buildStatus/icon?subject=Jenkins%20CI&job=LiveDate+Extensions%2Fmaster)
![Unit Tests](https://github.com/daydreamapps/LiveDataTools/workflows/Unit%20Tests/badge.svg)

Collection of utility &amp; extension functions for LiveData

## Structure
Like operations are grouped into Object classes with associated extension functions to permit them to be invoked inline.

```
object Filter {
    fun <T> filter(source: LiveData<T>, predicate: (T?) -> Boolean): LiveData<T> {
}

fun <T> LiveData<T>.filter(predicate: (T?) -> Boolean): LiveData<T> {
    return Filter.filter(this, predicate)
}
```

```
val liveData = MutableLiveData<String>()

val filteredLiveData = liveData.filter { it == "Hello World" }
```

## Testing

As each extension function links to an Object, the operation can be mocked using a that permits mocking of Kotlin Objects, such as [MockK](https://mockk.io/).

```
class Test.kt {

    @Before
    fun setUp() {
        mockkObject(Filter)
    }
    
    @Test
    fun `filter extension function calls object`() {
        val liveData = MutableLiveData<String>()
        val filteredLiveData = MutableLiveData<String>()
        
        every { Filter.filter(liveData, any() } returns filteredLiveData
        
        liveData.filter { true }
        
        verify { Filter.filter(liveData, any() }
    }

    @After
    fun tearDown() {
        unmockkObject(Filter)
    }
```
