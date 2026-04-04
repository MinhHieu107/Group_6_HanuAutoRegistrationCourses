# Design System Specification: The Academic Curator

This design system is a bespoke framework designed to elevate the university course registration experience from a mere utility to a sophisticated, editorial-grade digital environment. It balances the gravitas of a historic institution with the fluid, high-performance feel of modern software.

## 1. Creative North Star: The Digital Curator
The "Digital Curator" philosophy treats information not as data to be dumped, but as knowledge to be organized. We move away from the "cluttered spreadsheet" aesthetic and toward an "Editorial Workspace."

**Key Principles:**
*   **Intentional Asymmetry:** Use wide margins and offset headers to break the rigid grid, guiding the eye through complex schedules.
*   **Tonal Authority:** Trust is built through depth and whitespace, not borders and lines.
*   **Layered Intelligence:** Information is organized on "physical" planes, where the most critical actions float closest to the user.

## 2. Color & Surface Architecture
We move beyond flat hex codes to a system of **Tonal Layering**.

### The "No-Line" Rule
**Explicit Instruction:** Do not use 1px solid borders to section content. Boundaries must be defined exclusively through background shifts.
*   **Background:** Use `surface` (#f9f9f9) for the primary canvas.
*   **Sectioning:** Use `surface-container-low` (#f3f3f3) for large layout blocks.
*   **Nesting:** Place `surface-container-lowest` (#ffffff) cards on top of `surface-container-low` sections to create a natural, soft lift.

### Glass & Gradient Strategy
To avoid a "generic" portal feel, main CTAs and floating navigation should employ:
*   **Signature Gradients:** Transition from `primary` (#000666) to `primary-container` (#1a237e) to give buttons and hero headers "soul" and dimension.
*   **The Glass Rule:** For floating course drawers or search overlays, use `surface-container-lowest` at 80% opacity with a `24px` backdrop-blur. This ensures the schedule remains visible beneath the active task.

## 3. Typography: The Editorial Scale
We pair the high-character **Manrope** for display with the functional precision of **Inter** for data.

*   **Display & Headlines (Manrope):** Used for "The Big Picture"—enrollment status, semester titles, and page headers. The wide apertures of Manrope convey a modern, open institution.
*   **Body & Labels (Inter):** Used for "The Details"—course descriptions, credit hours, and time-slots. Inter’s high x-height ensures legibility in dense data tables.

**Scale Usage:**
*   **Display-LG (3.5rem):** For major milestones (e.g., "Registration Open").
*   **Title-MD (1.125rem):** The standard for course titles within cards.
*   **Label-MD (0.75rem):** Strict use for metadata (CRN numbers, prerequisites).

## 4. Elevation & Depth
Depth is a functional tool, not a decoration.

*   **Tonal Stacking:** Instead of shadows, stack `surface-container-high` (#e8e8e8) components inside `surface-container` (#eeeeee) areas to denote interactivity.
*   **Ambient Shadows:** For elements that truly float (like a "Register Now" floating action button), use a shadow with a `40px` blur, `4%` opacity, tinted with the `on-surface` (#1a1c1c) color. It should feel like a soft glow, not a dark smudge.
*   **The Ghost Border:** If a form field or course card requires a boundary for accessibility, use the `outline-variant` token at **15% opacity**. It should be felt, not seen.

## 5. Component Logic

### Course Cards & Data Tables
*   **No Dividers:** Forbid the use of horizontal rules between course listings. Use `1.5rem` (xl) vertical spacing or a subtle shift to `surface-container-low` on hover to separate items.
*   **The Timetable Grid:** The calendar component should use `surface-container-lowest` for the time blocks, sitting on a `surface` background. Use `primary-fixed-dim` (#bdc2ff) for "selected" slots to provide a sophisticated, muted highlight.

### Buttons & Interaction
*   **Primary:** A gradient fill (`primary` to `primary-container`) with `8px` (DEFAULT) roundedness. 
*   **Secondary/Ghost:** No background, using `primary` text. Upon hover, transition to a `surface-container-high` background.
*   **Chips (Status):**
    *   *Open:* `on-tertiary-container` (#e17c5a) text on a `tertiary-fixed` (#ffdbd0) background.
    *   *Full:* `on-error-container` (#93000a) text on `error-container` (#ffdad6).

### Inputs & Forms
*   **Focus State:** Instead of a heavy border, a focused input should trigger a `4px` soft glow using the `surface-tint` (#4c56af) color at 20% opacity.

## 6. Do’s and Don’ts

### Do
*   **Do** use `surface-container-highest` for "Selected" or "Active" states in the sidebar.
*   **Do** embrace whitespace. A course registration page should feel like a clean desk, not a crowded locker.
*   **Do** use `tertiary` tones (#380b00) for "Action Required" items to distinguish them from standard brand-blue tasks.

### Don’t
*   **Don't** use pure black (#000000) for text. Always use `on-surface` (#1a1c1c) to maintain a premium, ink-on-paper feel.
*   **Don't** use standard "Drop Shadows" on cards. Rely on the "No-Line" rule for elevation.
*   **Don't** use high-contrast dividers in data tables. Let the alignment of the typography create the vertical columns.