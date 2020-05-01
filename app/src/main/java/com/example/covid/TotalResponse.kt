package com.example.covid

data class TotalResponse(
	val casesTimeSeries: List<CasesTimeSeriesItem>,
	val tested: List<TestedItem?>? = null,
	val statewise: List<StatewiseItem>
)

data class CasesTimeSeriesItem(
	val date: String? = null,
	val dailyrecovered: String? = null,
	val totalconfirmed: String? = null,
	val totaldeceased: String? = null,
	val dailydeceased: String? = null,
	val totalrecovered: String? = null,
	val dailyconfirmed: String? = null
)

data class StatewiseItem(
	val statenotes: String? = null,
	val recovered: String? = null,
	val deltadeaths: String? = null,
	val deltarecovered: String? = null,
	val active: String? = null,
	val deltaconfirmed: String? = null,
	val state: String? = null,
	val statecode: String? = null,
	val confirmed: String? = null,
	val deaths: String? = null,
	val lastupdatedtime: String? = null
)

data class TestedItem(
	val testsperconfirmedcase: String? = null,
	val individualstestedperconfirmedcase: String? = null,
	val testpositivityrate: String? = null,
	val testsconductedbyprivatelabs: String? = null,
	val totalsamplestested: String? = null,
	val positivecasesfromsamplesreported: String? = null,
	val samplereportedtoday: String? = null,
	val source: String? = null,
	val updatetimestamp: String? = null,
	val totalindividualstested: String? = null,
	val totalpositivecases: String? = null
)

