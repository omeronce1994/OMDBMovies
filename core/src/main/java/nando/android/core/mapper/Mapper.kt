package nando.android.core.mapper

/**
 * Mapper used to map betweeen our data source model to features models
 *
 * @param FROM
 * @param TO
 */
interface Mapper<FROM, TO> {
    suspend fun map(from: FROM): TO
}