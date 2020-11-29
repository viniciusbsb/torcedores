import {RouteReuseStrategy} from '@angular/router/';
import {ActivatedRouteSnapshot, DetachedRouteHandle} from '@angular/router';

export class CacheRouteReuseStrategy implements RouteReuseStrategy {
    storedRouteHandles = new Map<string, DetachedRouteHandle>();
    allowRetriveCache: any = {
        'search-results': true
    };

    shouldReuseRoute(before: ActivatedRouteSnapshot, curr: ActivatedRouteSnapshot): boolean {
        this.allowRetriveCache['search-results'] = this.getPath(before) === 'detail' && this.getPath(curr) === 'search-result';
        return before.routeConfig === curr.routeConfig;
    }

    retrieve(route: ActivatedRouteSnapshot): DetachedRouteHandle | null {
        return this.storedRouteHandles.get(this.getPath(route)) as DetachedRouteHandle;
    }

    shouldAttach(route: ActivatedRouteSnapshot): boolean {
        const path = this.getPath(route);
        if (this.allowRetriveCache[path]) {
            return this.storedRouteHandles.has(this.getPath(route));
        }

        return false;
    }

    shouldDetach(route: ActivatedRouteSnapshot): boolean {
        const path = this.getPath(route);
        return this.allowRetriveCache.hasOwnProperty(path);

    }

    store(route: ActivatedRouteSnapshot, detachedTree: DetachedRouteHandle): void {
        this.storedRouteHandles.set(this.getPath(route), detachedTree);
    }

    private getPath(route: ActivatedRouteSnapshot): string  {
        if (route.routeConfig !== null && route.routeConfig.path !== null) {
            return route.routeConfig.path as string;
        }
        return '';
    }
}
